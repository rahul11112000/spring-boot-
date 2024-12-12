import axios from "axios";
const API_BASE_URL = "http://localhost:8080";

export const login = async (formData) => {
  try {
    const authHeader = `Basic ${btoa(
      `${formData.email}:${formData.password}`
    )}`;

    const response = await axios.post(
      `${API_BASE_URL}/token`,
      {},
      {
        headers: {
          Authorization: authHeader,
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error:", error.response?.data || error.message);
    throw error;
  }
};

export const wallet = async (token) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/client/wallet`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error("Error:", error.response?.data || error.message);
    throw error;
  }
};

export const addFund = async (token, amount) => {
  try {
    const response = await axios.put(
      `${API_BASE_URL}/client/add/money`,
      { amount },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error:", error.response?.data || error.message);
    throw error;
  }
};

export const create = async (token) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/client/create/wallet`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error:", error.response?.data || error.message);
    throw error;
  }
};

export const transfer = async (token, formData) => {
  try {
    const response = await axios.put(
      `${API_BASE_URL}/client/transfer/money`,
      formData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error:", error.response?.data || error.message);
    throw error;
  }
};
