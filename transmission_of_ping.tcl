set ns [new Simulator]

set ntrace [open prog2.tr w]
$ns trace-all $ntrace
set namfile [open prog2.nam w]
$ns namtrace-all $namfile

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n2 $n3 1Mb 10ms DropTail
$ns duplex-link $n3 $n4 1Mb 10ms DropTail
$ns duplex-link $n4 $n5 1Mb 10ms DropTail

Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "node [$node_ id] received ping answer from $from with round trip time $rtt ms"
}

set p0 [new Agent/Ping]
$p0 set class_ 1
$ns attach-agent $n0 $p0
set p1 [new Agent/Ping]
$ns attach-agent $n5 $p1
$ns connect $p0 $p1

$ns queue-limit $n2 $n3 2
$ns duplex-link-op $n2 $n3 queuePos 0.5

set tcp0 [new Agent/TCP]
$tcp0 set class_ 2
$ns attach-agent $n2 $tcp0
set sink0 [new Agent/TCPSink]
$ns attach-agent $n4 $sink0
$ns connect $tcp0 $sink0

set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set rate_ 1Mb
$cbr0 attach-agent $tcp0

$ns at 0.2 "$p0 send"
$ns at 0.4 "$p1 send"
$ns at 0.4 "$cbr0 start"
$ns at 0.8 "$p0 send"
$ns at 1.0 "$p1 send"
$ns at 1.2 "$cbr0 stop"
$ns at 1.4 "$p0 send"
$ns at 1.6 "$p1 send"
proc Finish {} {
    global ns ntrace namfile

    $ns flush-trace
    close $ntrace
    close $namfile

    exec nam prog2.nam &

    set dropped_packets [exec grep "^d" prog2.tr | awk "{print \$5}" | grep -c "ping"]
    puts "The number of ping packets dropped are $dropped_packets"
    exit 0
}

$ns at 1.8 "Finish"

$ns run
#ns: Finish: 0
#child process exited abnormally
 #   while executing
#"exec grep "^d" prog2.tr | awk "{print \$5}" | grep -c "ping""
#   (procedure "Finish" line 10)
#    invoked from within
#"Finish"
