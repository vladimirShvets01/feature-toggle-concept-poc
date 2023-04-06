import React, {useState} from "react";
import {useFlag} from "@unleash/proxy-client-react";
import './SendMiddlewareRequestComponent.css'


function SendMiddlewareRequestComponent() {
    const enabled = useFlag('feature-flag-5');

    const [responseText, setResponseText] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const sendRequest = async () => {
        setIsLoading(true);
        const response = await fetch("http://localhost:8080/lines/single?flag5=" + enabled);
        const text = await response.text();
        setResponseText(text);
        setIsLoading(false);
    };

    return <div className="flag-5-block">
        {enabled && (
            <div>
                <p>request sending available</p>
                <div>
                    <button onClick={sendRequest}>Send GET Request</button>
                    {isLoading && <p>Loading...</p>}
                    {responseText && <p>{responseText}</p>}
                </div>
            </div>
        )}
    </div>;
}

export default SendMiddlewareRequestComponent;