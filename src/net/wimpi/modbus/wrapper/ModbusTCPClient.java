package net.wimpi.modbus.wrapper;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadDeviceIdentificationRequest;
import net.wimpi.modbus.msg.ReadDeviceIdentificationResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import android.os.AsyncTask;
import android.util.Log;

/**
 * A wrapper class around the standard Jamod Modbus TCP connection and
 * transaction objects to allow easier usage
 * @author Saad Farooq
 *
 */
public class ModbusTCPClient {
	
	private static final String TAG = "ModbusTCPClient";
	
	ModbusTCPTransaction trans;
	TCPMasterConnection conn;
	
	public ModbusTCPClient() {
		trans = new ModbusTCPTransaction();
		conn = new TCPMasterConnection(null);
	}
	
	public ModbusTCPClient(String address) throws UnknownHostException {
		trans = new ModbusTCPTransaction();
		conn = new TCPMasterConnection(InetAddress.getByName(address));
	}
	
	public void setServer(String address, int port) throws UnknownHostException {
		conn.setAddress(InetAddress.getByName(address));
		conn.setPort(port);
	}

	public ReadInputRegistersResponse readInputRegisters(int startingRegisters, int registerCount, int slaveAddress) throws Exception {
		ReadInputRegistersRequest req = new ReadInputRegistersRequest(startingRegisters, registerCount, slaveAddress);
		return (ReadInputRegistersResponse) getResponse(req);
	}
	
	public ReadInputRegistersResponse readInputRegisters(String startingRegisters, String registerCount, String slaveAddress) throws Exception {
		return readInputRegisters(Integer.parseInt(startingRegisters), Integer.parseInt(registerCount), Integer.parseInt(slaveAddress));
	}
	
	
	public ReadDeviceIdentificationResponse readDeviceIdentification(int slaveAddress) throws Exception {
		ReadDeviceIdentificationRequest req = new ReadDeviceIdentificationRequest(slaveAddress);
		return (ReadDeviceIdentificationResponse) getResponse(req);
	}
	
	public ReadDeviceIdentificationResponse readDeviceIdentification(String slaveAddress) throws Exception {
		return readDeviceIdentification(Integer.parseInt(slaveAddress));
	}

	/**
	 * Get the response for the passed ModbusRequest subclass in a background thread.
	 * The method waits until a response is received.
	 * @param req the Modbus request to transmit
	 * @return the ModbusResponse object returned by server
	 * @throws Exception
	 */
	private ModbusResponse getResponse(final ModbusRequest req) throws Exception {
		ModbusResponse response = new AsyncTask<Void, Void, ModbusResponse>() {
			@Override
			protected ModbusResponse doInBackground(Void... params) {
				try {
					conn.connect();
					trans.setConnection(conn);
					trans.setRequest(req);
					trans.execute();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
					return null;
				}
				return trans.getResponse();
			}
			
		}.execute().get();
		
		return response;
		
		}
	
	
	public ModbusResponse getPollResponse(final ModbusRequest req) throws Exception {
		ModbusResponse response = new AsyncTask<Void, Void, ModbusResponse>() {
			@Override
			protected ModbusResponse doInBackground(Void... params) {
				try {
					conn.connect();
					conn.setTimeout(1000);
					Log.e(TAG, "Timeout: "+conn.getTimeout());
					trans.setConnection(conn);
					trans.setRequest(req);
					trans.execute();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
					return null;
				}
				return trans.getResponse();
			}
			
		}.execute().get();
		
		return response;
	}
	
}