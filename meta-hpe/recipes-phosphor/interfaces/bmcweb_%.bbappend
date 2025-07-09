FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Message-registry-changes-for-IML-SL-IEL.patch \
            file://0002-fix-increase-buffer-size-in-websocket-connection-imp.patch \
           "

EXTRA_OEMESON:append = " \
    -Dhttp-body-limit=65 \
    "

# prepend disabled to override journal default 'on'; readd if dbg build
EXTRA_OEMESON:prepend = " \
     -Dredfish-bmc-journal=disabled \
    "

# enabled for all builds
EXTRA_OEMESON:append = " \
    -Dredfish-dump-log=enabled \
    "
