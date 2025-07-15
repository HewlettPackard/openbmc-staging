FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://80-gxp-obmc-console-uart.rules \
            file://sshd-console.socket \
            file://sshd-console@.service \
            file://sshd_console_config \
           "

inherit obmc-phosphor-systemd

do_install:append() {
    # Replace the upstream-provided obmc-console-uart rule with gxp uart rule.
    rm -f ${D}/${libdir}/udev/rules.d/80-obmc-console-uart.rules
    install -m 0644 ${UNPACKDIR}/80-gxp-obmc-console-uart.rules ${D}/${libdir}/udev/rules.d

     # Install console SSH service files
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/sshd-console.socket ${D}${systemd_system_unitdir}/
    install -m 0644 ${UNPACKDIR}/sshd-console@.service ${D}${systemd_system_unitdir}/

    # Install console-specific SSH config
    install -d ${D}${sysconfdir}/ssh
    install -m 0644 ${UNPACKDIR}/sshd_console_config ${D}${sysconfdir}/ssh/

    # Substitute paths in systemd service file
    sed -i -e 's,@SBINDIR@,${sbindir},g' \
           -e 's,@SYSCONFDIR@,${sysconfdir},g' \
           ${D}${systemd_system_unitdir}/sshd-console@.service
}

# disable ssh port 2200 as it only supports dropbear
PACKAGECONFIG:remove = "ssh"

# Add openssh dependency for console SSH access
RDEPENDS:${PN} += "openssh-sshd"
