HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native python3-setuptools-native"

LICENSE = "GPL-2.0-or-later"
PE = "1"
CVE_PRODUCT = "u-boot"
PROVIDES += "u-boot"

SRC_URI = "git://git@github.com/HewlettPackard/gsc-uboot.git;protocol=ssh;branch=${BRANCH}"
BRANCH ?= "gsc-${PV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
UNPACKDIR = "${WORKDIR}/sources-unpack"

# use supplied configuration fragment
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://fw_env.config \
                   file://u-boot-env.cfg \
                   file://uboot_default_environment \
                 "


inherit pkgconfig

do_configure[cleandirs] = "${B}"
UBOOT_ENVIRONMENT_FILE = "uboot_default_environment"

do_configure:append:gsc () {
    if [ -f "${UNPACKDIR}/${UBOOT_ENVIRONMENT_FILE}" ]; then
        cp "${UNPACKDIR}/${UBOOT_ENVIRONMENT_FILE}" "${S}/board/hpe/gsc/gsc.env"
    else
        bbplain "Using default U-Boot Environment from U-Boot Repo"
    fi
}
