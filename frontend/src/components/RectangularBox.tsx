import React from 'react';
import './RectangularBox.css';
import { useFlag } from '@unleash/proxy-client-react';

interface Props {
    orderNumber: number;
}

const RectangularBox: React.FC<Props> = ({orderNumber}) => {
    const enabled = useFlag('feature-flag-' + orderNumber.toString())
    console.log(enabled)
    const text = enabled
        ? 'Feature flag ' + orderNumber.toString() + '\nEnabled'
        : 'Feature flag ' + orderNumber.toString() + '\nDisabled';
    return (<div className={`rectangular-box ${enabled ? 'enabled' : 'disabled'}`}>
        <p>{text}</p>
    </div>);
};

export default RectangularBox;