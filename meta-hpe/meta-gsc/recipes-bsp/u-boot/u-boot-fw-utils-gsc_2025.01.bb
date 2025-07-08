require recipes-bsp/u-boot/u-boot.inc
require u-boot-gsc.inc

SRCREV = "50fee8f668f2b0b18ba1b166d44a11136c785905"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

SUMMARY = "U-Boot bootloader fw_printenv/setenv utilities"
DEPENDS += "mtd-utils"
PROVIDES = "u-boot-fw-utils"

INSANE_SKIP:${PN} = "already-stripped"

EXTRA_OEMAKE:class-target = 'CROSS_COMPILE="${TARGET_PREFIX}" HOSTCC="${BUILD_CC} ${BUILD_FLAGS} ${BUILD_LDFLAGS}" CC="${CC} ${CFLAGS} ${LDFLAGS}" STRIP=true V=1'
EXTRA_OEMAKE:class-cross = 'ARCH=${TARGET_ARCH} CC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'

do_deploy[noexec] = "1"

do_compile () {
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake envtools
}

do_install () {
	install -d ${D}${base_sbindir}
	install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
	install -m 755 ${B}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv

	install -d ${D}${sysconfdir}
	install -m 644 ${UNPACKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

FILES:${PN} += " \
     ${base_sbindir}/fw_printenv \
     ${base_sbindir}/fw_setenv \
  "
