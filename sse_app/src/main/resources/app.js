const eventSource = new EventSource("http://localhost:8080/time");

eventSource.onopen = (event) => {
    console.log("connection opened")
}

eventSource.onmessage = (event) => {
    console.log("result", event.data);
}

eventSource.onerror = (event) => {
    console.log("onError")
    console.log(event.target.readyState)
    if (event.target.readyState === EventSource.CLOSED) {
        console.log('eventsource closed (' + event.target.readyState + ')')
    }
    eventSource.close();
}

eventSource.addEventListener("Custom", (event) => {
    const result = JSON.parse(event.data);
    console.log(`received: ${result.message}, time: ${result.date}`);
});