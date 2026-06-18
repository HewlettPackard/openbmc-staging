SUMMARY = "Common Image for HPE ProLiant"
SRC_URI = " \
  file://set-environment.service \
  file://load-dtbo.service \
  file://load-dtbo.sh \
  file://proliant_0x0264.dtso \
  file://proliant_0x0265.dtso \
  file://proliant_0x0266.dtso \
  file://proliant_0x0273.dtso \
  file://proliant_0x0284.dtso \
  file://proliant_0x0285.dtso \
  "

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd

SYSTEMD_SERVICE:${PN} = " \
  set-environment.service \
  load-dtbo.service \
  "

S = "${UNPACKDIR}"

DEPENDS:append = " dtc-native"


do_compile() {
  dtc -I dts -o ${WORKDIR}/proliant_0x0264.dtbo ${UNPACKDIR}/proliant_0x0264.dtso
  dtc -I dts -o ${WORKDIR}/proliant_0x0265.dtbo ${UNPACKDIR}/proliant_0x0265.dtso
  dtc -I dts -o ${WORKDIR}/proliant_0x0266.dtbo ${UNPACKDIR}/proliant_0x0266.dtso
  dtc -I dts -o ${WORKDIR}/proliant_0x0273.dtbo ${UNPACKDIR}/proliant_0x0273.dtso
  dtc -I dts -o ${WORKDIR}/proliant_0x0284.dtbo ${UNPACKDIR}/proliant_0x0284.dtso
  dtc -I dts -o ${WORKDIR}/proliant_0x0285.dtbo ${UNPACKDIR}/proliant_0x0285.dtso
}

do_install() {
  install -d ${D}/boot
  install -m 0755 ${WORKDIR}/proliant_0x0264.dtbo ${D}/boot/proliant_0x0264.dtbo
  install -m 0755 ${WORKDIR}/proliant_0x0265.dtbo ${D}/boot/proliant_0x0265.dtbo
  install -m 0755 ${WORKDIR}/proliant_0x0266.dtbo ${D}/boot/proliant_0x0266.dtbo
  install -m 0755 ${WORKDIR}/proliant_0x0273.dtbo ${D}/boot/proliant_0x0273.dtbo
  install -m 0755 ${WORKDIR}/proliant_0x0284.dtbo ${D}/boot/proliant_0x0284.dtbo
  install -m 0755 ${WORKDIR}/proliant_0x0285.dtbo ${D}/boot/proliant_0x0285.dtbo
  install -d ${D}/etc/profile.d
  echo 'BOARD=`cat /sys/devices/platform/ahb@80000000/d1000000.xreg/soc/xreg/server_id`' > ${D}/etc/profile.d/set_board_env.sh
  echo "export BOARD" >> ${D}/etc/profile.d/set_board_env.sh

  install -d ${D}${systemd_system_unitdir}
  install -m 0644 ${UNPACKDIR}/set-environment.service ${D}${systemd_system_unitdir}/
  install -m 0644 ${UNPACKDIR}/load-dtbo.service ${D}${systemd_system_unitdir}/

  install -d ${D}${libexecdir}
  install -m 0755 ${UNPACKDIR}/load-dtbo.sh ${D}${libexecdir}/load-dtbo.sh
}

FILES:${PN} += " \
  /etc/profile.d/set_board_env.sh \
  ${libexecdir}/load-dtbo.sh \
  /boot/* \
  "
