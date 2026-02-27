inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRCREV = "0c81397fb7a5ba6f54921c92ad1eae3b2d0b868f"
KBRANCH = "gsc-linux-6.12.62"
SRC_URI = "git://github.com/HewlettPackard/gsc-linux.git;protocol=https;branch=${KBRANCH};depth=1 \
           file://gsc_defconfig \
           "

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PROVIDES += "virtual/kernel"
KCONFIG_MODE = "--alldefconfig"

PV = "${LINUX_VERSION}+git"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KMETA_AUDIT_WERROR = "1"

LINUX_VERSION ?= "6.12.62"

# Block install of full kernel image in /boot of the rootfs
# https://docs.yoctoproject.org/kernel-dev/faq.html#how-do-i-install-not-install-the-kernel-image-on-the-root-filesystem
RRECOMMENDS:${KERNEL_PACKAGE_NAME}-base = ""

KERNEL_DTC_FLAGS += "-@"
