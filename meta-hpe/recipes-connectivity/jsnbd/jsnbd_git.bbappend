FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

FILES:${PN} += "${sysconfdir}/nbd-proxy/state"
SRC_URI += "file://state_hook \
            file://0001-fix-Use-the-nbd-client-g-parameter-to-disable-the-us.patch \
            "

do_install:append() {
    install -d ${D}${sysconfdir}/nbd-proxy/
    install -m 0755 ${UNPACKDIR}/state_hook ${D}${sysconfdir}/nbd-proxy/state
}
