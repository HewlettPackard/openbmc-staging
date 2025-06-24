SUMMARY = "HPE ProLiant Support"
DESCRIPTION = "Recipes to support HPE ProLiant server hardware and firmware"

BRANCH = "main"
SRC_URI = "git://github.com/HewlettPackard/openbmc-proliant-support.git;branch=${BRANCH};protocol=https"
SRCREV = "a5bbdeb7d3a7bd1725669a5adb64f08b7098b968"

PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit meson

S = "${WORKDIR}/git"
