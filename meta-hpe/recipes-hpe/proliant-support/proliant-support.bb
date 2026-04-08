SUMMARY = "HPE ProLiant Support"
DESCRIPTION = "Recipes to support HPE ProLiant server hardware and firmware"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-proliant-support.git;branch=${BRANCH};protocol=https"
SRCREV = "be41cc5df6f7dfe5a14fbf375476f81b0e42a116"

PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS += " \
        boost \
        systemd \
        sdbusplus \
        "

inherit meson pkgconfig systemd

SYSTEMD_SERVICE:${PN} += " host-state-monitor.service"
SYSTEMD_SERVICE:${PN} += " gsc-fru-service.service"
# Enable the service by default
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

S = "${WORKDIR}/git"
