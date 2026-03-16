SUMMARY = "HPE GSC chif service"
DESCRIPTION = "HPE CHIF service to support BIOS"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-chif-svc.git;branch=${BRANCH};protocol=https"
SRCREV = "0889d236545f8b1b3b53d25bec0b23cce16e0a2d"

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
