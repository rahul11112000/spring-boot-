import React, { useState, useEffect } from "react";
import { wallet, create } from "../api/Api";

const WalletPage = () => {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await wallet(token); // Replace with your API endpoint
        setUserData(response);
        if (response == null) {
          const createWallet = await create(token);
          // const response = await wallet(token); // Replace with your API endpoint
          setUserData(createWallet);
        }
      } catch (err) {
        console.error("Error fetching user data:", err);
        setError("Failed to fetch user data.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [token]);

  if (loading) {
    return <div className="text-center mt-5">Loading...</div>;
  }

  if (error) {
    return <div className="alert alert-danger text-center">{error}</div>;
  }

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "24rem" }}>
        <h2 className="card-title text-center mb-4">Digital Wallet</h2>
        <div className="card-body">
          <p className="card-text">
            <strong>Name:</strong> {userData.user.name}
          </p>
          <p className="card-text">
            <strong>Email:</strong> {userData.user.email}
          </p>
          <p className="card-text">
            <strong>Wallet Amount:</strong> ${userData.balance.toFixed(2)}
          </p>
          <div className="d-grid gap-2 mt-3">
            {/* <button className="btn btn-primary">
              View Transaction History
            </button> */}
            <a href="/add/fund" className="btn btn-primary">
              Add fund
            </a>
            <a href="/transfer/money" className="btn btn-primary">
              Transfer Money
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WalletPage;
