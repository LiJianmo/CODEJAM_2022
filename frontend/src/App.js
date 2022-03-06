import './App.css';
import { useState } from 'react';
import Form from './Form/Form';
import { List, ListItem, ListItemText } from '@material-ui/core';
import GoogleMapReact from 'google-map-react';
import { BsFillFlagFill } from 'react-icons/bs';

const PointMarker = ({ text }) => {return (<div className={`${text}`} style={{ color: 'red' }}> {text} <BsFillFlagFill size={30}/></div>)};
const defaultProps = {
    center: {
      lat: 45.501690,
      lng: -73.567253
    },
    zoom: 4
  };
const App = () => {
  // const [result, setResult] = useState([]);
  const [result, setResult] = useState([]);
  const [lat, setLat] = useState(-999);
  const [lng, setLng] = useState(-999);

  const [endLat, setEndLat] = useState(-999);
  const [endLng, setEndLng] = useState(-999);
  return (
    <div className="App">
        <div>
        <div style={{ height: '50vh', width: '50%', marginLeft: '25%', marginTop: '5vh' }}>
      <GoogleMapReact
        defaultCenter={defaultProps.center}
        defaultZoom={defaultProps.zoom}
      >
        <PointMarker
          lat={lat}
          lng={lng}
          text="Start"  
          className="Start"
        />
        <PointMarker
          lat={endLat}
          lng={endLng}
          text="End"  
          className="End"
        />
      </GoogleMapReact>
    </div>
        </div>
      <Form setState={setResult} setLat={setLat} setLng={setLng}/>
      {
            result.length ?
            (<List sx={{ width: '100%' }}>
                {result.map((r) => (
                  <ListItem>
                    <button onClick={()=>{setLat(r.start.latitude); setLng(r.start.longitude); setEndLat(r.destination.latitude); setEndLng(r.destination.longitude);}}>
                    Show me on map!
                    </button>
                  <ListItemText style={{textAlignVertical: "center",textAlign: "center",}} primary={`Load ID: ${r.loadID}, Profit: ${r.profit}, Start: ${r.start.latitude}, ${r.start.longitude}, End: ${r.destination.latitude}, ${r.destination.longitude}`} />
                  </ListItem>
              ))}
            </List>) : <div/>
          }
    </div>
  );
}

export default App;