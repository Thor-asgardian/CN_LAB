# Create a simulator
set ns [new Simulator]

# Create two nodes
set n0 [$ns node]
set n1 [$ns node]

# Create a duplex link between nodes
$ns duplex-link $n0 $n1 10Mb 10ms DropTail

# Set up TCP connection
set tcp0 [new Agent/TCP]
set tcp1 [new Agent/TCP]

$ns attach-agent $n0 $tcp0
$ns attach-agent $n1 $tcp1

set sink0 [new Agent/TCPSink]
set sink1 [new Agent/TCPSink]

$ns attach-agent $n1 $sink0
$ns attach-agent $n0 $sink1

$ns connect $tcp0 $sink0
$ns connect $tcp1 $sink1

# Set up traffic
set cbr0 [new Application/Traffic/CBR]
$cbr0 set type_ CBR
$cbr0 set packetSize_ 1000
$cbr0 set rate_ 1Mb
$cbr0 set random_ false
$cbr0 attach-agent $tcp0

set cbr1 [new Application/Traffic/CBR]
$cbr1 set type_ CBR
$cbr1 set packetSize_ 1000
$cbr1 set rate_ 1Mb
$cbr1 set random_ false
$cbr1 attach-agent $tcp1

# Schedule traffic start
$ns at 1.0 "$cbr0 start"
$ns at 1.0 "$cbr1 start"

# Enable tracing of congestion window
$tcp0 trace cwnd $ns
$tcp1 trace cwnd $ns

# Run the simulation
$ns run

# Function to plot congestion window
proc plot_cwnd {tcpTraceFile title} {
    set plotCmd "set terminal png size 800,600; set output 'cwnd_plot.png'; set title '$title'; set xlabel 'Time (seconds)'; set ylabel 'Congestion Window'; plot '$tcpTraceFile' using 1:5 with lines title 'Node 0', '$tcpTraceFile' using 1:11 with lines title 'Node 1'"
    exec echo $plotCmd | gnuplot
}

# Plot congestion window
plot_cwnd "out.tr" "Congestion Window Over Time"
