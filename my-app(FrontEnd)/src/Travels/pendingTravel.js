import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./history.css";
import { Link } from 'react-router-dom';

const TravelList = () => {
  const [travelData, setTravelData] = useState([]);
  const [loading, setLoading] = useState(true);


  const fetchData = async () => {
    try {
        const authToken = localStorage.getItem('accessToken');
    console.log(authToken);
      const response = await axios.post('http://localhost:8081/PendingTravel',{},
      {  
        headers: {
            Authorization: `Bearer ${authToken}`,
        },
    });
    setLoading(false);

    if (response.data === null || response.data.length === 0) {
      setTravelData([]); // Set travelData to an empty array if details are not found
    } else {
      setTravelData(response.data);
    }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  // Fetch data when the component mounts
  useEffect(() => {
    fetchData();
  }, []);

  const handleEdit = async(id) => {
    console.log(`Edit item with ID: ${id}`);
    const authToken = localStorage.getItem('accessToken');
    console.log(authToken);
    await axios.delete('http://localhost:8081/CancelJourney',{
      
        
        headers: {
            Authorization: `Bearer ${authToken}`,
        },
        data: {
            id: id
          },
    });
    window.alert("Ticket is cancelled. Refund will be credeted back to you in 2 business days.\nTHANK YOU!");
   window.location.reload();
    
  };

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
    <div className="TravelData">
      <h1>Your Pending Journey List</h1>
      {loading ? (
        <p>Loading...</p>
      ) : travelData.length === 0 ? (
        <p>No pending Journey Ticket details found.</p> // Display message when details are not found
      ) : (
        <table>
          <thead>
            <tr>
              <th>Source</th>
              <th>Destination</th>
              <th>Journey Date</th>
              <th>Passenger Number</th>
              <th>Price</th>
              <th>Edit</th>
            </tr>
          </thead>
          <tbody>
            {travelData.map((item) => (
              <tr key={item.id}>
                <td>{item.source}</td>
                <td>{item.destination}</td>
                <td>{item.journeyDate}</td>
                <td>{item.passengerNum}</td>
                <td>{item.price}</td>
                <td>
                  <button onClick={() => handleEdit(item.id)}>Cancel</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
    </div>
  );
};

export default TravelList;
