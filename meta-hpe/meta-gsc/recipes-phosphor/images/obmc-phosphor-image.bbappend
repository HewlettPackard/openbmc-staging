# This is typically inherited via IMAGE_CLASSES in phosphor-base.inc, but
# bitbake does not properly allow redefining functions from bbclasses that are
# not directly inherited, so it is explicitly inherited here.
inherit image_types_phosphor

# Add bash completion packages to the image
IMAGE_FEATURES:append = " bash-completion-pkgs"

# Use OpenSSH instead of Dropbear
IMAGE_FEATURES:remove = "ssh-server-dropbear"
IMAGE_FEATURES:append = " ssh-server-openssh"

# TODO: Consider moving do_sign_uboot to the u-boot recipe
python do_sign_uboot() {
    import pathlib
    import shlex
    import subprocess

    uboot_signing_key = d.getVar("UBOOT_SIGNING_KEY")
    if uboot_signing_key is None:
        bb.fatal(
            "UBOOT_SIGNING_KEY must be set to the absolute path of the signing key"
        )
    uboot_signing_key_path = pathlib.Path(uboot_signing_key)
    if not uboot_signing_key_path.absolute():
        bb.fatal(
            f"UBOOT_SIGNING_KEY must be an absolute path, not {uboot_signing_key}"
        )

    # DEPLOY_DIR_IMAGE should be treated as read-only to read files that other
    # recipes have deployed
    deploy_dir_image = pathlib.Path(d.getVar("DEPLOY_DIR_IMAGE"))
    # IMGDEPLOYDIR is writeable, bitbake will take care of pushing the final
    # directory contents to DEPLOY_DIR_IMAGE
    img_deploy_dir = pathlib.Path(d.getVar("IMGDEPLOYDIR"))

    u_boot_bin = deploy_dir_image / "u-boot.bin"
    u_boot_padded = img_deploy_dir / "u-boot-padded.bin"
    u_boot_signature = img_deploy_dir / "u-boot.sig"
    u_boot_signed = img_deploy_dir / "u-boot.signed"

    # Pad the u-boot.bin file to the size of FLASH_UBOOT_SIGNING_SIZE
    u_boot_padded.write_bytes(
        u_boot_bin.read_bytes().ljust(
            int(d.getVar("FLASH_UBOOT_SIGNING_SIZE")) * 1024, b"\xFF"
        )
    )

    try:
        # Sign the padded u-boot file
        openssl_cmd = (
            f"openssl dgst -sha384 -sign {uboot_signing_key_path} "
            f"-out {u_boot_signature} {u_boot_padded}"
        )
        result = subprocess.run(shlex.split(openssl_cmd), capture_output=True, text=True)
    except subprocess.CalledProcessError as e:
        bb.fatal(
            f"Failed to use openssl to sign the extended U-Boot {e.returncode}: "
            f"{e.stdout} {e.stderr}"
        )

    # Prepend a header to the U-Boot signature, this is required for the
    # bootloader to identify the signature
    u_boot_header_512 = b"$start$\x00$boot1$\x00\x00\x02\x00\x00"
    signature = u_boot_header_512 + u_boot_signature.read_bytes()
    # Pad the signature to 65536 bytes
    u_boot_signed.write_bytes(signature.ljust(65536, b"\0"))
}

# Override the default do_generate_image_uboot_file function to avoid an
# improperly offset U-Boot binary when generating a ROM file
do_generate_image_uboot_file() {
    image_dst=$1

    dd bs=1k \
        if=${IMGDEPLOYDIR}/u-boot-padded.bin \
        of=${image_dst}
}

python do_generate_static:prepend() {
    bb.build.exec_func("do_sign_uboot", d)
}

python do_generate_static:append() {
    u_boot_signature_offset = int(d.getVar('FLASH_UBOOT_SIGNATURE_OFFSET', True))
    u_boot_signing_size = int(d.getVar('FLASH_UBOOT_SIGNING_SIZE', True))
    _append_image(os.path.join(d.getVar('IMGDEPLOYDIR', True),
                            "u-boot.signed"),
                u_boot_signature_offset,
                u_boot_signature_offset + u_boot_signing_size)
}

do_mk_static_symlinks:append() {
    ln -sf u-boot.signed ${IMGDEPLOYDIR}/image-u-boot-signature
}

make_image_links:append() {
    ln -sf ${IMGDEPLOYDIR}/u-boot.signed image-u-boot-signature
}

make_tar_of_images() {
    type=$1
    shift
    extra_files="$@"

    # Create the tar archive
    tar -h -cvf ${IMGDEPLOYDIR}/${IMAGE_NAME}.$type.tar \
        image-u-boot image-u-boot-signature image-kernel image-rofs image-rwfs $extra_files

    cd ${IMGDEPLOYDIR}
    ln -sf ${IMAGE_NAME}.$type.tar ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.$type.tar
}

do_generate_static_tar() {
    ln -sf ${S}/MANIFEST MANIFEST
    ln -sf ${S}/publickey publickey
    make_image_links ${OVERLAY_BASETYPE} ${IMAGE_BASETYPE}
    make_signatures image-u-boot image-kernel image-rofs image-rwfs MANIFEST publickey image-u-boot-signature
    make_tar_of_images static.mtd MANIFEST publickey ${signature_files}

    # Maintain non-standard legacy link.
    cd ${IMGDEPLOYDIR}
    ln -sf ${IMAGE_NAME}.static.mtd.tar ${IMGDEPLOYDIR}/${MACHINE}-${DATETIME}.tar
}
do_generate_static_tar[depends] += "${PN}:do_generate_static"
