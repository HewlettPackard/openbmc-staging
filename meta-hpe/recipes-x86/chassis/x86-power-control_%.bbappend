FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Fix-x86-power-control-issues.patch"
SRC_URI += "file://pwr_util"

RDEPENDS:${PN}-updater:append = " bash"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/pwr_util ${D}${bindir}/pwr
}
