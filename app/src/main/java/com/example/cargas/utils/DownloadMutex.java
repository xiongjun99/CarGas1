package com.example.cargas.utils;

import java.util.HashMap;

import com.example.cargas.communication.HttpDownloadTask;


public class DownloadMutex {
	
	public static HashMap<String, HttpDownloadTask> _download_feedback_tasks = new HashMap<String, HttpDownloadTask>();
	public static HashMap<String, HttpDownloadTask> _download_task_tasks = new HashMap<String, HttpDownloadTask>();
	

}
