# Package summary
SUMMARY = "service to enable SAFS and VROM"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
BRANCH = "main"
SRCREV = "39a4fa037cbb0c95a91aff6858ea5e740722c506"
SRC_URI = "git://github.com/HewlettPackard/safs-vrom-service.git;protocol=https;branch=${BRANCH}"


SYSTEMD_SERVICE:${PN} += "com.hpe.bmc.safsvrom.service"
SYSTEMD_AUTO_ENABLE = "enable"

DEPENDS += "systemd phosphor-logging"
RDEPENDS:${PN} += "systemd phosphor-logging"


PV = "1.0+git"

inherit meson pkgconfig systemd
