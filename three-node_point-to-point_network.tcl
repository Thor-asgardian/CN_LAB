# Create a simulator object
set ns [new Simulator]

# Enable tracing of events
set tracefile [open three_node_network.tr w]
$ns trace-all $tracefile

# Create three nodes
set node(0) [$ns node]
set node(1) [$ns node]
set node(2) [$ns node]

# Create duplex links between nodes
set link(0) [$ns duplex-link $node(0) $node(1) 10Mb 5ms DropTail]
set link(1) [$ns duplex-link $node(1) $node(2) 5Mb 10ms DropTail]

# Set the queue size for the links
$link(0) queue-limit 10
$link(1) queue-limit 5

# Create a TCP traffic source and sink
set tcp_source [new Agent/TCP]
set tcp_sink [new Agent/TCPSink]

# Attach the TCP agents to the nodes
$ns attach-agent $node(0) $tcp_source
$ns attach-agent $node(2) $tcp_sink

# Create a traffic source and attach it to the TCP agent
set traffic_source [new Application/Traffic/CBR]
$traffic_source attach-agent $tcp_source

# Set the parameters for the traffic source
$traffic_source set type_ CBR
$traffic_source set packetSize_ 1000
$traffic_source set rate_ 1mb
$traffic_source set interval_ 0.01

# Schedule the traffic source to start at time 1.0s
$ns at 1.0 "$traffic_source start"

# Schedule the simulation to run for 5 seconds
$ns at 5.0 "$ns stop"

# Run the simulation
$ns run

# Close the trace file
close $tracefile
