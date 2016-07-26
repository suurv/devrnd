#!/bin/bash
cat <<EOF > ~/.vimrc
set ai
set encoding=utf-8
set ts=8
set sw=8
syn off
set t_Co=0
set t_md=
set nowrap
set shortmess=atI
EOF

cat <<EOF > ~/.screenrc
shelltitle ""
escape ^kk
startup_message off
defscrollback 9000
bind k focus up
bind j focus down
caption always "%{0}<<<%n:%t>>>"
EOF

cat <<EOF >> ~/.bashrc
export PS1='\u@\h $ '
unalias ls
set -o vi
EOF

