# Package summary
SUMMARY = "gxp-fru-device"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

BRANCH = "main"

BB_STRICT_CHECKSUM = "0"
SRCREV = "a07805320b2fa2b018787b39413bafbe83f4cdaf"

SRC_URI = "git://git@github.hpe.com/glider/gxp-fru-device.git;protocol=ssh;branch=${BRANCH}"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-fix-newer-cxx-std.patch"
SRC_URI += "file://0002-fix-update-io_service-to-io_context.patch"

S = "${WORKDIR}/git"
PV = "1.0+git"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "boost nlohmann-json sdbusplus i2c-tools libgpiod valijson"

SYSTEMD_SERVICE:${PN} += "xyz.openbmc_project.GxpFruDevice.service"

inherit cmake systemd

EXTRA_OECMAKE = ""

