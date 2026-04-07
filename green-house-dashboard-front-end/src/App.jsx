import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Sprout, RefreshCw } from 'lucide-react';
import ZoneCard from './components/ZoneCard';

function App() {
  const [zones, setZones] = useState([]);
  const [telemetryData, setTelemetryData] = useState({}); // New state for live data
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch zones exactly once on load
  useEffect(() => {
    fetchZones();
  }, []);

  // Poll for live telemetry every 5 seconds
  useEffect(() => {
    const fetchLiveTelemetry = async () => {
      try {
        const response = await axios.get('http://localhost:8080/telemetry-service/api/sensors/latest');
        const latestReading = response.data;
        
        // Update the state map using the zoneId so the correct card gets the data
        if (latestReading && latestReading.zoneId) {
          setTelemetryData(prevData => ({
            ...prevData,
            [latestReading.zoneId]: latestReading
          }));
        }
      } catch (err) {
        console.error("Error fetching live telemetry:", err);
        // We don't set a main error here so it doesn't break the UI if one request fails
      }
    };

    // Run immediately, then every 5 seconds
    fetchLiveTelemetry();
    const interval = setInterval(fetchLiveTelemetry, 5000);
    
    // Cleanup interval on unmount
    return () => clearInterval(interval);
  }, []);

  const fetchZones = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get('http://localhost:8080/zone-service/api/zones');
      setZones(response.data);
    } catch (err) {
      setError("Could not connect to the Backend.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 font-sans">
      <nav className="bg-white border-b border-gray-200 px-8 py-4 flex items-center justify-between sticky top-0 z-10 shadow-sm">
        <div className="flex items-center space-x-3">
          <div className="bg-green-600 p-2 rounded-lg">
            <Sprout className="w-6 h-6 text-white" />
          </div>
          <div>
            <h1 className="text-xl font-extrabold text-gray-900 tracking-tight">AgriSmart Dashboard</h1>
            <p className="text-xs text-gray-500 font-medium">Automated Greenhouse Management System</p>
          </div>
        </div>
        <button 
          onClick={fetchZones}
          className="flex items-center space-x-2 bg-green-50 hover:bg-green-100 text-green-700 px-4 py-2 rounded-lg font-semibold transition-colors border border-green-200"
        >
          <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
          <span>Refresh Zones</span>
        </button>
      </nav>

      <main className="max-w-7xl mx-auto px-8 py-8">
        <div className="flex justify-between items-end mb-6">
          <div>
            <h2 className="text-2xl font-bold text-gray-800">Active Zones</h2>
            <p className="text-gray-500 text-sm mt-1">Monitoring {zones.length} greenhouse sections</p>
          </div>
          <div className="flex items-center space-x-2">
             <span className="relative flex h-3 w-3">
               <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"></span>
               <span className="relative inline-flex rounded-full h-3 w-3 bg-green-500"></span>
             </span>
             <span className="text-sm font-medium text-gray-600">Live Telemetry Active</span>
          </div>
        </div>

        {loading && zones.length === 0 && (
          <div className="flex flex-col items-center justify-center h-64 bg-white rounded-2xl border border-gray-100 border-dashed">
            <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-green-600 mb-4"></div>
            <p className="text-gray-500 font-medium">Loading zone configurations...</p>
          </div>
        )}

        {error && (
          <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded-xl shadow-sm">
            <p className="text-red-700 font-medium">{error}</p>
          </div>
        )}

        {!loading && !error && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {zones.map((zone) => (
              <ZoneCard 
                key={zone.id || zone.name} 
                zone={zone} 
                // Pass the specific telemetry data for this zone based on its device ID or Name
                telemetry={telemetryData[zone.deviceId] || telemetryData[zone.name]} 
              />
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default App;