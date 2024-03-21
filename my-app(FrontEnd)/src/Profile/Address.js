import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import "./profile.css";
import { jwtDecode } from 'jwt-decode';

function Address() {
    const [addressLine1, setAddressLine1] = useState('');
    const [addressLine2, setAddressLine2] = useState('');
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');
    const [postalCode, setPostalCode] = useState('');
    const [state, setState] = useState('');
    const [primary, setPrimary] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchAddressData();
    }, []);

    const fetchAddressData = async () => {
        try {
            const authToken = localStorage.getItem('accessToken');
            const decodedToken = jwtDecode(authToken);
            const userRole = decodedToken.role;

            let response;

            if (userRole === "USER") {
                response = await axios.get('http://localhost:8081/user/getAddress', {
                    headers: {
                        Authorization: `Bearer ${authToken}`,
                    },
                });
            } else if (userRole === "ADMIN") {
                response = await axios.get('http://localhost:8081/admin/getAddress', {
                    headers: {
                        Authorization: `Bearer ${authToken}`,
                    },
                });
            }

            const { data } = response;
            if (!data) {
                setLoading(false);
                return;
            }

            setAddressLine1(data.addressLine1 || "-");
            setAddressLine2(data.addressLine2 || "-");
            setCity(data.city || "-");
            setCountry(data.country || "-");
            setPostalCode(data.postalCode || "-");
            setState(data.state || "-");
            setPrimary(data.primary || false);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching address data:', error.message);
            setLoading(false);
        }
    };

    return (
        <div className="home">
            <Link to={"/home"} className="link"><button id='home'>Back to home</button></Link>
            <div className="profile">
                {loading ? (
                    <p>Loading...</p>
                ) : (
                    <>
                        <table>
                            <tbody>
                                <tr>
                                    <td><h3>Present Address</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{addressLine1}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>Permanent Address</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{addressLine2}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>Country</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{country}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>State</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{state}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>City</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{city}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>Postal Code</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{postalCode}</p></td>
                                </tr>
                                <tr>
                                    <td><h3>Is Present Address same<br /> as Permanent Address?</h3></td>
                                    <td><h4>:</h4></td>
                                    <td><p>{primary ? "Yes" : "No"}</p></td>
                                </tr>
                            </tbody>
                        </table>
                        {!country && (
                            <Link to={"/updateAddress"} class="link">
                                <button>Create Address</button>
                            </Link>
                        )}
                        {country && (
                            <Link to={"/updateAddress"} class="link">
                                <button>Update Address</button>
                            </Link>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}

export default Address;
