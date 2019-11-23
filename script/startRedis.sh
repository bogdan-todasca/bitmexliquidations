#ping redis server
redis-cli ping

#stop redis
redis-cli shutdown

#start redis
redis-server

#print all keys
redis-cli KEYS '*'

#delete all
redis-cli FLUSHALL