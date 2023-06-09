/* Protocol 1 (Utopia) provides for data transmission in one direction only, from
sender to receiver. The communication channel is assumed to be error free
and the receiver is assumed to be able to process all the input infinitely quickly.
Consequently, the sender just sits in a loop pumping data out onto the line as
fast as it can. */
typedef enum {frame_arrival} event_type;
#include "protocol.h"

void sender1(void)
{
	frame s; /* buffer for an outbound frame */
	packet buffer; /* buffer for an outbound packet */
	while (true) {
		from_network_layer(&buffer); /* go get something to send */
		s.info = buffer; /* copy it into s for transmission */
		to_physical_layer(&s); /* send it on its way */
	}  
}

void receiver1(void)
{
	frame r;
	event_type event; /* filled in by wait, but not used here */
	while (true) {
		wait_for_event(&event); /* only possibility is frame arrival */
		from_physical_layer(&r); /* go get the inbound frame */
		to_network_layer(&r.info); /* pass the data to the network layer */
	}
}