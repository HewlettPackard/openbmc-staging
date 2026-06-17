SUMMARY = "Enable Host Boot"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit obmc-phosphor-systemd

HOST_BOOT_SERVICE = "host-boot-enable.service"
SYSTEMD_SERVICE:${PN} += "${HOST_BOOT_SERVICE}"

HOST_BOOT_FMT = "../${HOST_BOOT_SERVICE}:multi-user.target.wants/${HOST_BOOT_SERVICE}"
SYSTEMD_LINK_${PN} += "${HOST_BOOT_FMT}"

SRC_URI += "file://host-boot-enable.service"
SRC_URI:append = " file://host-boot-enable.sh \
                   file://host-boot-enable_0x0000.sh \
                   file://host-boot-enable_0x0261.sh \
                   file://host-boot-enable_0x0264.sh \
                   file://host-boot-enable_0x0265.sh \
                   file://host-boot-enable_0x0266.sh \
                   file://host-boot-enable_0x0273.sh \
                   file://host-boot-enable_0x0284.sh \
                   file://host-boot-enable_0x0285.sh \
                 "

S = "${UNPACKDIR}"

do_install:append() {
    install -d ${D}/usr/bin
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable.sh ${D}/usr/bin/host-boot-enable.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0000.sh ${D}/usr/bin/host-boot-enable_0x0000.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0261.sh ${D}/usr/bin/host-boot-enable_0x0261.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0264.sh ${D}/usr/bin/host-boot-enable_0x0264.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0265.sh ${D}/usr/bin/host-boot-enable_0x0265.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0266.sh ${D}/usr/bin/host-boot-enable_0x0266.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0273.sh ${D}/usr/bin/host-boot-enable_0x0273.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0284.sh ${D}/usr/bin/host-boot-enable_0x0284.sh
    install -m 0755 -D ${UNPACKDIR}/host-boot-enable_0x0285.sh ${D}/usr/bin/host-boot-enable_0x0285.sh
}
