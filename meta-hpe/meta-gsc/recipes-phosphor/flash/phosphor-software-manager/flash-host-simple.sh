#!/bin/bash
#
# Simple Host BIOS flash for Gen12 - directly writes to /dev/mtd/by-name/host-prime
# No host power management needed for Gen12 because no support for RL300
#

IMG_PATH="$1"

if [ -z "$IMG_PATH" ] || [ ! -d "$IMG_PATH" ]; then
    echo "ERROR: Image directory not found: $IMG_PATH"
    exit 1
fi

# Find the ROM image file
IMAGE=$(find "$IMG_PATH" -type f \( -name "*.rom" -o -name "*.bin" -o -name "*.img" -o -name "image-host.rom" \) | head -n 1)

if [ -z "$IMAGE" ] || [ ! -f "$IMAGE" ]; then
    echo "ERROR: No ROM image found in $IMG_PATH"
    ls -la "$IMG_PATH"
    exit 1
fi

echo "Found image: $IMAGE"
echo "Flashing to /dev/mtd/by-name/host-prime..."

# Flash directly to host-prime MTD partition
if [ ! -c /dev/mtd/by-name/host-prime ]; then
    echo "ERROR: /dev/mtd/by-name/host-prime not found"
    exit 1
fi

# Use flashcp for safer flashing (erases and verifies)
flashcp -v "$IMAGE" /dev/mtd/by-name/host-prime

if [ $? -eq 0 ]; then
    echo "Host BIOS flash completed successfully"
    exit 0
else
    echo "ERROR: Host BIOS Flash failed"
    exit 1
fi
