# Create Simulator
set ns [new Simulator]

# Open Trace file
set ntrace [open ping_trace.tr w]
$ns trace-all $ntrace

# Create 6 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

# Create Links between nodes
$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n2 $n3 1Mb 10ms DropTail
$ns duplex-link $n3 $n4 1Mb 10ms DropTail
$ns duplex-link $n4 $n5 1Mb 10ms DropTail

# Set Queue Size
$ns queue-limit $n0 $n1 10
$ns queue-limit $n1 $n2 10
$ns queue-limit $n2 $n3 10
$ns queue-limit $n3 $n4 10
$ns queue-limit $n4 $n5 10

# Set up a simple ping application
set pingApp [new Application/Traffic/Ping]
$pingApp attach-agent [$n0 agent]
$ns at 0.5 "$pingApp start"

# Schedule Finish Procedure
$ns at 5.0 "Finish"

# Finish Procedure
proc Finish {} {
    global ns ntrace

    # Dump trace data and close file
    $ns flush-trace
    close $ntrace

    # Show the number of packets dropped
    exec echo "The number of packet drops is " & exec grep -c "^d" ping_trace.tr &

    exit 0
}

# Run the Simulation
$ns run
