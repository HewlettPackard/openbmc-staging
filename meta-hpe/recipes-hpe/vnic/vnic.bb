SUMMARY = "configure gadget to enable vNIC device"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SYSTEMD_SERVICE:${PN} += "com.hpe.bmc.vnic-init.service"
SYSTEMD_SERVICE:${PN} += "com.hpe.bmc.vnic-control.service"


SRC_URI = "file://com.hpe.bmc.vnic-control.service \
           file://com.hpe.bmc.vnic-init.service \
           file://vnic-gadget.sh \
           file://00-bmc-usb0.network "

DEPENDS += "systemd"
RDEPENDS:${PN} += " bash"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

inherit obmc-phosphor-systemd pkgconfig

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/vnic-gadget.sh ${D}${bindir}
    install -d ${D}${sysconfdir_native}/systemd/network/
    install -m 0644  ${UNPACKDIR}/00-bmc-usb0.network  ${D}${sysconfdir}/systemd/network
}

FILESEXTRAPATHS:prepend := "${THISDIR}:"
