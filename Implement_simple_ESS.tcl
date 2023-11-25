# Create a new simulator instance
set ns [new Simulator]

# Enable wireless extension
$ns use-wireless $ns

# Create a topology
set node_(0) [$ns node]
set node_(1) [$ns node]
set node_(2) [$ns node]
set node_(3) [$ns node]

# Create a wireless channel
set chan [new Channel/WirelessChannel]

# Create a wireless propagation model
set prop [new Propagation/TwoRayGround]

# Create a wireless interface
$ns duplex-link $node_(0) $node_(1) 1Mb 10ms $chan $prop
$ns duplex-link $node_(1) $node_(2) 1Mb 10ms $chan $prop
$ns duplex-link $node_(2) $node_(3) 1Mb 10ms $chan $prop

# Create a wireless LAN
set wlan [new LanNode/WirelessNode]

# Attach nodes to the wireless LAN
$wlan add $node_(0) $node_(1) $node_(2) $node_(3)

# Configure MAC and PHY layers
$ns node-config -adhocRouting OFF
$ns node-config -llType LL
$ns node-config -macType Mac/802_11
$ns node-config -ifqType Queue/DropTail/PriQueue
$ns node-config -ifqLen 50
$ns node-config -antType Antenna/OmniAntenna
$ns node-config -propType Propagation/TwoRayGround
$ns node-config -phyType Phy/WirelessPhy
$ns node-config -channel $chan

# Create a CBR traffic source
set cbr [new Agent/UDP]
$ns attach-agent $node_(0) $cbr

set udp [new Application/Traffic/CBR]
$udp attach-agent $cbr
$udp set packetSize_ 500
$udp set interval_ 0.5
$udp set random_ false

# Create a UDP sink
set sink [new Agent/Null]
$ns attach-agent $node_(3) $sink

# Connect the CBR source to the sink
$ns connect $cbr $sink

# Schedule events
$ns at 0.0 "$udp start"
$ns at 5.0 "$ns stop"

# Run the simulation
$ns run
