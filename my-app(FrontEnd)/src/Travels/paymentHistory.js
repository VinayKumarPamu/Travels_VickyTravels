import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./history.css";
import { Link } from 'react-router-dom';

const TravelList = () => {
  const [travelData, setTravelData] = useState([]);

  const fetchData = async () => {
    try {
        const authToken = localStorage.getItem('accessToken');
    console.log(authToken);
      const response = await axios.post('http://localhost:8081/history',{},
      {  
        headers: {
            Authorization: `Bearer ${authToken}`,
        },
    });
      setTravelData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  // Fetch data when the component mounts
  useEffect(() => {
    fetchData();
  }, []);

 

  return (
    <div className="home">
    <div id='cssmenu' >
        <ul>
        <li><Link to="/home" class='link' ><span>Home</span></Link></li>
            <li><Link to='/bookTicket'class='link' ><span>PLAN your Journey</span></Link></li>
            <li><Link to='/paymentHistory' ><span>payments</span></Link></li>
            <li><Link to='/tickets'><span>tickets</span></Link></li>
            <li><Link to='/history' class='link'><span>History</span></Link></li>
            <li><Link to="/"class="link" ><span>LogOut</span></Link></li>
        </ul>
    </div>
    <div class="TravelData">
      <h1>Payment History</h1>
      <table>
        <thead>
          <tr>
          <th>Journey Date</th>
          <th>Price</th>
          <th>Passenger Number</th>
            <th>Source</th>
            <th>Destination</th>
            </tr>
        </thead>
        <tbody>
          {travelData.map((item) => (
            <tr key={item.id}>
             <td>{item.journeyDate}</td>
             <td>{item.price}</td>
             <td>{item.passengerNum}</td>
              <td>{item.source}</td>
              <td>{item.destination}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    </div>
  );
};

export default TravelList;
