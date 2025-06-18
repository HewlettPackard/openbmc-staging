FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " file://10-systemd-conf.conf"

# Set ForwardToSyslog=no
# Disable forward journal to socket since that is not being used.
do_install:append() {
  install -D -m0644 ${UNPACKDIR}/10-systemd-conf.conf ${D}${systemd_unitdir}/journald.conf.d/10-systemd-conf.conf
}
