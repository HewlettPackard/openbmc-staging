#if we support ipmi fake out dropbear service as ssh
do_install:append () {
        if [ "${@bb.utils.filter('DISTRO_FEATURES', 'pam', d)}" ]; then
                ln -sf ${sysconfdir}/pam.d/sshd ${D}${sysconfdir}/pam.d/dropbear
        fi
}
FILES:${PN} += " ${sysconfdir}/pam.d/dropbear"
