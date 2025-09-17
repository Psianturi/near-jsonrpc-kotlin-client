package com.near.jsonrpc.client

import com.near.jsonrpc.JsonRpcError

class NearRpcException(message: String, val error: JsonRpcError) : RuntimeException(message)

