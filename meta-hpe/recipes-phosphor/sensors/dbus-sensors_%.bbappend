FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-support-for-TMP1075-tempsensors.patch \
            file://0001-now-supports-gpio-based-fail-bit-monitoring-as-well-.patch \
            "
SRC_URI += "file://0002-FanMain-geared-up-for-only-HPEFan-support-for-now.patch"
SRC_URI += "file://0003-IntelCPU-now-has-a-deltaTempFilter-for-reporting-tem.patch"

