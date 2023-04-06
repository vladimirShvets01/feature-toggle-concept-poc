import React, {useState} from "react";
import './DatabaseSchemaStatus.css'


function DatabaseSchemaStatus() {
    const [responseText, setResponseText] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const sendRequest = async () => {
        setIsLoading(true);
        const response = await fetch("http://localhost:8080/lines/schema");
        const text = await response.text();
        setResponseText(text);
        setIsLoading(false);
    };

    return <div className="flag-5-block">

                <p>Current DB schema</p>
                <div>
                    <button onClick={sendRequest}>Send GET Request</button>
                    {isLoading && <p>Loading...</p>}
                    {responseText && <p>{responseText}</p>}
                </div>

    </div>;
}

export default DatabaseSchemaStatus;