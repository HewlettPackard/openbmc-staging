SUMMARY = "HPE ProLiant Support"
DESCRIPTION = "Recipes to support HPE ProLiant server hardware and firmware"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-proliant-support.git;branch=${BRANCH};protocol=https"
SRCREV = "4f85bb01794371b40a16a0f28192dd72904b8d79"

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
