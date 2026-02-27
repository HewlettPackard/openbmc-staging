
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# platform configuration files
PACKAGECONFIG:append = " dts-vpd"
SRC_URI += "file://dl340g12.json"
SRC_URI += "file://dl360-dl380g12.json"
SRC_URI += "file://0001-Entity-Manager-support-for-cooling-cooled_by-associa.patch"

do_install:append() {
    install -D ${UNPACKDIR}/dl340g12.json ${D}${datadir}/${BPN}/configurations/dl340g12.json
    install -D ${UNPACKDIR}/dl360-dl380g12.json ${D}${datadir}/${BPN}/configurations/dl360-dl380g12.json
}
