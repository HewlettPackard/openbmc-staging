
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# platform configuration files
PACKAGECONFIG:append = " dts-vpd"
SRC_URI += "file://dl320g12_baseboard.json"
SRC_URI += "file://dl340g12_baseboard.json"
SRC_URI += "file://dl345g12_baseboard.json"
SRC_URI += "file://dl360g12_baseboard.json"
SRC_URI += "file://dl380g12_baseboard.json"
SRC_URI += "file://0001-Entity-Manager-support-for-cooling-cooled_by-associa.patch"

do_install:append() {
    install -D ${UNPACKDIR}/dl320g12_baseboard.json ${D}${datadir}/${BPN}/configurations/hpe/dl320g12_baseboard.json
    install -D ${UNPACKDIR}/dl340g12_baseboard.json ${D}${datadir}/${BPN}/configurations/hpe/dl340g12_baseboard.json
    install -D ${UNPACKDIR}/dl345g12_baseboard.json ${D}${datadir}/${BPN}/configurations/hpe/dl345g12_baseboard.json
    install -D ${UNPACKDIR}/dl360g12_baseboard.json ${D}${datadir}/${BPN}/configurations/hpe/dl360g12_baseboard.json
    install -D ${UNPACKDIR}/dl380g12_baseboard.json ${D}${datadir}/${BPN}/configurations/hpe/dl380g12_baseboard.json
}
