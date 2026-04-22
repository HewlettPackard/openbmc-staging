SUMMARY = "HPE GSC chif service"
DESCRIPTION = "HPE CHIF service to support BIOS"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-chif-svc.git;branch=${BRANCH};protocol=https"
SRCREV = "75f98cc221bd17a9ed00cd8b404ccccd2183934a"

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



inherit meson obmc-phosphor-systemd pkgconfig
