FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-feat-allow-optional-images-for-static-updater.patch"

EXTRA_OEMESON:append = " \
    -Doptional-images='image-u-boot-signature' \
    "
