FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://80-gxp-obmc-console-uart.rules"

do_install:append() {
    # Replace the upstream-provided obmc-console-uart rule with gxp uart rule.
    rm -f ${D}/${libdir}/udev/rules.d/80-obmc-console-uart.rules
    install -m 0644 ${UNPACKDIR}/80-gxp-obmc-console-uart.rules ${D}/${libdir}/udev/rules.d
}

# disable ssh port 2200 as it only supports dropbear
PACKAGECONFIG:remove = "ssh"
