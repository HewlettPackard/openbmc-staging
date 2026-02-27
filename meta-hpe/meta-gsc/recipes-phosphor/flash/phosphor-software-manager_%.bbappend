FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-feat-allow-optional-images-for-static-updater.patch \
    file://0002-hack-for-host-fw-update.patch \
    file://flash-host-simple.sh \
    file://obmc-flash-host-bios@.service \
    "

# Always enable Host BIOS upgrade support
EXTRA_OEMESON:append = " \
    -Dhost-bios-upgrade=enabled \
    -Doptional-images='image-u-boot-signature' \
    "

SYSTEMD_SERVICE:${PN}-updater += "obmc-flash-host-bios@.service"

RDEPENDS:${PN} += "bash proliant-support"

do_install:append() {
    # Install flash script
    install -d ${D}/usr/sbin
    install -m 0755 ${UNPACKDIR}/flash-host-simple.sh ${D}/usr/sbin/flash-host-simple.sh
    
    # Install systemd service override
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/obmc-flash-host-bios@.service ${D}${systemd_system_unitdir}/obmc-flash-host-bios@.service
}
