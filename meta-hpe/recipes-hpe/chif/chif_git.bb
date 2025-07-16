SUMMARY = "HPE GSC chif service"
DESCRIPTION = "HPE CHIF service to support BIOS"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-chif-svc.git;branch=${BRANCH};protocol=https"
SRCREV = "ae5d2948b9da23456c88a45ce081c1f4d2755277"

PV = "0.1+git${SRCPV}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SYSTEMD_SERVICE:${PN} += " xyz.openbmc_project.GxpChif.service"

DEPENDS += " \
        systemd \
        sdbusplus \
        phosphor-dbus-interfaces \
        phosphor-logging \
        "


S = "${WORKDIR}/git"

inherit meson obmc-phosphor-systemd pkgconfig
