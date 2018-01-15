
#---------------------------------#
#      general configuration      #
#---------------------------------#

$urlPath = "https://nmap.org/npcap/dist/npcap-0.97.exe"
$checksum = "d5d8c9ea81a57a25a406a03b8b3186e19e972a43c71b315d4e28e0ff2d42f526"

# Download the file
wget -c $urlPath -UseBasicParsing -OutFile $PSScriptRoot"\npcap.exe"

# Now let's check its checksum
$_chksum = $(CertUtil -hashfile $PSScriptRoot"\npcap.exe" SHA256)[1] -replace " ",""
if ($_chksum -ne $checksum){
    echo "Checksums does NOT match !"
    exit
} else {
    echo "Checksums matches !"
}

# Run installer
Start-Process $PSScriptRoot"\npcap.exe" -ArgumentList "/S /npf_startup=yes /loopback_support=yes /dlt_null=no /admin_only=no /dot11_support=no /vlan_support=no /winpcap_mode=yes" -wait
echo "Npcap installation completed"
