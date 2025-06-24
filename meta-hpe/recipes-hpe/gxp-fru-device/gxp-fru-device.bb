# Package summary
SUMMARY = "gxp-fru-device"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

BRANCH = "main"

BB_STRICT_CHECKSUM = "0"
SRCREV = "2a1b3205a5e4b539fa6e1d7921b319777629ed92"

SRC_URI = "git://git@github.com/HewlettPackard/openbmc-gxp-fru-device.git;protocol=ssh;branch=${BRANCH}"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0000-baseline.patch"
SRC_URI += "file://0001-fix-newer-cxx-std.patch"
SRC_URI += "file://0002-fix-update-io_service-to-io_context.patch"

S = "${WORKDIR}/git"
PV = "1.0+git"

DEPENDS = "boost nlohmann-json sdbusplus i2c-tools libgpiod valijson"

SYSTEMD_SERVICE:${PN} += "xyz.openbmc_project.GxpFruDevice.service"

inherit cmake systemd

EXTRA_OECMAKE = ""

