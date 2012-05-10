package net.wimpi.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.ProcessImageFactory;

public class ReadDeviceIdentificationResponse 
	extends ModbusResponse {
	
	 //instance attributes
	private int m_ByteCount;
	private int m_MEIType;
	private int m_DeviceIDCode;
	private int m_ConfirmityLevel;
	private int m_ObjectCount;
	private String[] m_DeviceIdentication;
	private int m_MoreFollows;
	private int m_NextObjectId;

	@Override
	public void writeData(DataOutput dout) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readData(DataInput din) throws IOException {
//		din.skipBytes(8);
		m_MEIType = din.readUnsignedByte();
		m_DeviceIDCode = din.readUnsignedByte();
		m_ConfirmityLevel = din.readUnsignedByte();
		m_MoreFollows = din.readUnsignedByte();
		m_NextObjectId = din.readUnsignedByte();
		m_ObjectCount = din.readUnsignedByte();
		m_DeviceIdentication = new String[m_ObjectCount];
		
		for (int i = 0; i < m_ObjectCount; i ++) {
			int objectId = din.readUnsignedByte();
			int objectLength = din.readUnsignedByte();
			byte[] b = new byte[objectLength];
			din.readFully(b);
			m_DeviceIdentication[i] = new String(b);
		}
	   
	  }//readData

	public int getMEIType() {
		return m_MEIType;
	}

	public int getDeviceIDCode() {
		return m_DeviceIDCode;
	}

	public int getConfirmityLevel() {
		return m_ConfirmityLevel;
	}

	public int getObjectCount() {
		return m_ObjectCount;
	}

	public String[] getDeviceIdentication() {
		return m_DeviceIdentication;
	}

	public int getMoreFollows() {
		return m_MoreFollows;
	}

	public int getNextObjectId() {
		return m_NextObjectId;
	}

}
