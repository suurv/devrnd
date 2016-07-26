cat <<EOF > ~/.screenrc
shelltitle ""
escape ^ff
startup_message off
defscrollback 9000
bind k focus up
bind j focus down
caption always "%{0}<<<EXT %n:%t>>>"
EOF
