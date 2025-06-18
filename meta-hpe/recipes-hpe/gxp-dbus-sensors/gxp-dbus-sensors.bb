# Package summary
SUMMARY = "gxp-dbus-sensors"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

BRANCH = "main"

BB_STRICT_CHECKSUM = "0"
SRCREV = "daa578b8aa10e54a1e1f42f4d7800187728cbb5e"

SRC_URI = "git://git@github.com/HewlettPackard/openbmc-gxp-dbus-sensors.git;protocol=https;branch=${BRANCH}"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://CMakeLists.patch file://findfans.patch file://fixTempManagedObjects.patch"
SRC_URI += "file://0001-fix-update-io_service-to-io_context.patch"
SRC_URI += "file://0002-fix-update-io.post-to-boost-asio-post.patch"
SRC_URI += "file://0003-don-t-build-or-install-the-gxp-fan-sensors-daemon.patch"

S = "${WORKDIR}/git"
PV = "1.0+git"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "boost nlohmann-json libgpiod sdbusplus"

SYSTEMD_SERVICE:${PN} += "xyz.openbmc_project.GxpTempSensor.service"

inherit cmake systemd

EXTRA_OECMAKE = ""
