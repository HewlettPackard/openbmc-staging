do_install:append () {
	install -d ${D}${base_sbindir}
        ln -sf /usr/bin/fw_printenv ${D}${base_sbindir}/fw_printenv
	ln -sf fw_printenv ${D}${base_sbindir}/fw_setenv
}
